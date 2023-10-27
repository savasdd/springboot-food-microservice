import {Injectable} from "@angular/core";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";
import {UserAuthController} from "./api-gateway-api";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private service: UserAuthController) {
  }

  findAllUser(loadOptions: any) {
    return this.service.getGatewayAllUser().pipe(catchError(this.handleError));
  }

  getUserGroup(id: any) {
    return this.service.getGatewayUserGroup(id).pipe(catchError(this.handleError));
  }

  findAllRoll(loadOptions: any) {
    return this.service.getGatewayRoles().pipe(catchError(this.handleError));
  }

  findAllGroup(loadOptions: any) {
    return this.service.getGatewayGroup().pipe(catchError(this.handleError));
  }


  saveUser(data: any) {
    return this.service.createGatewayUser(data).pipe(catchError(this.handleError));
  }

  saveRol(data: any) {
    return this.service.createGatewayRole(data).pipe(catchError(this.handleError));
  }

  saveGroup(data: any) {
    return this.service.createGatewayGroup(data).pipe(catchError(this.handleError));
  }

  joinUserGroup(data: any) {
    return this.service.joinGatewayUserGroup(data).pipe(catchError(this.handleError));
  }

  deleteUser(id: any) {
    return this.service.deleteGatewayUser(id).pipe(catchError(this.handleError));
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
