import {Injectable} from "@angular/core";
import {catchError, Observable, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";
import {AuthControllerService, TokenResponse, UserDto} from "./gateway-service-api";


@Injectable({
  providedIn: 'root'
})
export class GatewayService {

  constructor(private service: AuthControllerService) {
  }

  getToken(dto: UserDto) {
    // return this.service.getToken(dto).pipe(catchError(this.handleError));
    return this.service.getToken(dto);
  }


  refreshToken(dto: TokenResponse): Observable<any> {
    return this.service.refreshToken(dto).pipe(catchError(this.handleError));
  }


  private handleError(error: HttpErrorResponse) {
    const service = new MessageService;
    if (error.status === 0) {
      service.error(error.error.errorMessage);
      console.error('An error occurred:', error.error);
    } else {
      service.error(error.error.errorMessage);
      console.error(`Backend returned code ${error.status}, body was: `, error.error);
    }
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }

}
