import { Injectable } from "@angular/core";
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { MessageService } from "../../services/message.service";
import { catchError, Observable, throwError } from "rxjs";
import { Router } from "@angular/router";


@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(
    private messageService: MessageService,
    private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError(err => {
      if (err.status === 401) {
        this.messageService.error(err.error.errorMessage);
        console.log("Hata 401: " + err.error.errorMessage)
      }

      if (err.status === 403) {
        this.messageService.error('Yetkisiz Erişim: ' + err.error.errorMessage);
        console.log("Yetkisiz Erişim")
        //this.router.navigate(['/login/status/forbidden']);
      }

      if (err.status === 404) {
        this.messageService.error(err.error.errorMessage);
        console.log("Hata 404: " + err.error.errorMessage)
      }

      if (err.status === 400) {
        if (err.error && err.error.errors) {

        } else if (err.error) {
          this.messageService.error(err.error.errorMessage);
        }
      }

      if (err.status === 503 || err.status === 504) {
        this.messageService.error('Service Unavailable!!');
      }

      if (err.status === 500 || err.status === 0) {
        this.messageService.error('Üzgünüz! Sistemde beklenmedik bir hata oluştu. Lütfen tekrar deneyiniz!');
      }

      const error = err.error.message || err.statusText;
      return throwError(error);
    }));
  }
}
