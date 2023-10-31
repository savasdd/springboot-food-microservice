import {Injectable} from "@angular/core";
import {BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError} from "rxjs";
import {TokenService} from "../service/token.service";
import {AuthService} from "../service/auth.service";
import {Router} from "@angular/router";
import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from "@angular/common/http";
import {JwtHelperService} from "@auth0/angular-jwt";

const TOKEN_HEADER_KEY = 'Authorization';


@Injectable()
export class Auth implements HttpInterceptor {
  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
  public jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private tokenService: TokenService,
              private authService: AuthService,
              private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<Object>> {
    let authReq = req;
    const token = this.tokenService.getToken();

    if (token != null) {
      authReq = this.addTokenHeader(req, token);
    }

    return next.handle(authReq).pipe(catchError(error => {
      if (error instanceof HttpErrorResponse && !authReq.url.includes('login') && error.status === 401) {
        return this.handle401Error(authReq, next);
      }

      return throwError(error);
    }));
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      const token = this.tokenService.getRefreshToken();
      if (token)
        return this.authService.refreshToken().pipe(
          switchMap((token: any) => {
            this.isRefreshing = false;
            this.tokenService.saveToken(token.access_token);
            this.tokenService.saveRefreshToken(token.refresh_token);
            this.tokenService.saveRol(token.roles);
            this.tokenService.saveUser(this.tokenService.getUser());
            this.refreshTokenSubject.next(token.access_token);

            return next.handle(this.addTokenHeader(request, token.access_token));
          }),
          catchError((err) => {
            this.isRefreshing = false;

            this.tokenService.signOut();
            return throwError(err);
          })
        );
    }

    return this.refreshTokenSubject.pipe(
      filter(token => token !== null),
      take(1),
      switchMap((token) => next.handle(this.addTokenHeader(request, token)))
    );
  }

  private addTokenHeader(request: HttpRequest<any>, token: string) {
    const tokenIsExpired = this.jwtHelper.isTokenExpired(token);
    const refreshTokenIsExpired = this.jwtHelper.isTokenExpired(this.tokenService.getRefreshToken());
    if (!refreshTokenIsExpired && tokenIsExpired) {
      console.log('Token is expired: ' + tokenIsExpired);
      return request;
    } else {
      return request.clone({headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token)});
    }

  }

}

export const authInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: Auth, multi: true}
];
