import {Injectable} from "@angular/core";
import {catchError, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";
import {GroupControllerService, RolControllerService, UserControllerService} from "./gateway-service-api";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private userService: UserControllerService,
              private rolService: RolControllerService,
              private groupService: GroupControllerService) {
  }

  findAllUser(loadOptions: any) {
    return this.userService.getAllUser().pipe(catchError(this.handleError));
  }

  getUserGroup(id: any) {
    return this.userService.getUserGroup(id).pipe(catchError(this.handleError));
  }

  findAllRoll(loadOptions: any) {
    return this.rolService.getRoles().pipe(catchError(this.handleError));
  }

  findAllGroup(loadOptions: any) {
    return this.groupService.getGroup().pipe(catchError(this.handleError));
  }

  getGroupRol(id: any) {
    return this.groupService.getGroupRol(id).pipe(catchError(this.handleError));
  }

  saveUser(data: any) {
    return this.userService.createUser(data).pipe(catchError(this.handleError));
  }

  saveRol(data: any) {
    return this.rolService.createRole(data).pipe(catchError(this.handleError));
  }

  saveGroup(data: any) {
    return this.groupService.createGroup(data).pipe(catchError(this.handleError));
  }

  joinUserGroup(data: any) {
    return this.userService.joinUserGroup(data).pipe(catchError(this.handleError));
  }

  addGroupRol(data: any) {
    return this.groupService.addRolGroup(data).pipe(catchError(this.handleError));
  }

  leaveUserGroup(data: any) {
    return this.userService.leaveUserGroup(data).pipe(catchError(this.handleError));
  }

  leaveGroupRol(data: any) {
    return this.groupService.leaveGroupRol(data).pipe(catchError(this.handleError));
  }


  deleteUser(id: any) {
    return this.userService.deleteUser(id).pipe(catchError(this.handleError));
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
