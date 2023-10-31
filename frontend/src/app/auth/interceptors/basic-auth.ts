import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {SessionStorageService} from "angular-web-storage";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";


@Injectable()
export class BasicAuth implements HttpInterceptor {
  constructor(public sessionStorage: SessionStorageService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.sessionStorage.get("BasicAuth");
    const isApiUrl = request.url.startsWith(environment.apiUrl);
    if (isApiUrl && token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Basic ${token}`
        }
      });
    }

    return next.handle(request);
  }
}
