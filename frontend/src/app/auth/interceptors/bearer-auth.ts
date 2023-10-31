import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {SessionStorageService} from "angular-web-storage";
import {TokenService} from "../service/token.service";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";


@Injectable()
export class BearerAuthInterceptor implements HttpInterceptor {
  constructor(public sessionStorage: SessionStorageService,
              private token: TokenService,) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.token.getToken();
    const isApiUrl = request.url.startsWith(environment.apiUrl);
    if (isApiUrl && token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        }
      });
    }
    return next.handle(request);
  }
}
