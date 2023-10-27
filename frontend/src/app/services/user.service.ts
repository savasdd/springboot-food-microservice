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
    return this.service.getAllUser().pipe(catchError(this.handleError));
  }

  getUserGroup(id: any) {
    return this.service.getUserGroup(id).pipe(catchError(this.handleError));
  }

  findAllRoll(loadOptions: any) {
    return this.service.getRoles().pipe(catchError(this.handleError));
  }

  findAllGroup(loadOptions: any) {
    return this.service.getGroups().pipe(catchError(this.handleError));
  }

  getGroupRol(id: any) {
    return this.service.getGroupRol(id).pipe(catchError(this.handleError));
  }

  saveUser(data: any) {
    return this.service.createUser(data).pipe(catchError(this.handleError));
  }

  saveRol(data: any) {
    return this.service.createRole(data).pipe(catchError(this.handleError));
  }

  saveGroup(data: any) {
    return this.service.createGroup(data).pipe(catchError(this.handleError));
  }

  joinUserGroup(data: any) {
    return this.service.joinUserGroup(data).pipe(catchError(this.handleError));
  }

  addGroupRol(data: any) {
    return this.service.addGroupRol(data).pipe(catchError(this.handleError));
  }

  leaveUserGroup(data: any) {
    return this.service.leaveUserGroup(data).pipe(catchError(this.handleError));
  }

  leaveGroupRol(data: any) {
    return this.service.leaveGroupRol(data).pipe(catchError(this.handleError));
  }



  deleteUser(id: any) {
    return this.service.deleteUser(id).pipe(catchError(this.handleError));
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
