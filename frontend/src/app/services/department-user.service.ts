import {Injectable} from "@angular/core";
import {Category, CategoryControllerService} from "./food-service-api";
import {catchError, Observable, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";
import {DepartmentControllerService, UserDepartmentControllerService} from "./user-service-api";


@Injectable({
  providedIn: 'root'
})
export class DepartmentUserService {

  constructor(private service: UserDepartmentControllerService) {
  }

  findAll(loadOptions: any) {
    return this.service.getAllDepartmentUserLoad(loadOptions).pipe(catchError(this.handleError));
  }

  findAlls(): Observable<any> {
    return this.service.getAllDepartmentUser().pipe(catchError(this.handleError));
  }

  findOne(id: number): Observable<any> {
    return this.service.getOneDepartmentUser(id).pipe(catchError(this.handleError));
  }

  save(data: any) {
    return this.service.createDepartmentUser(data).pipe(catchError(this.handleError));
  }

  update(id: number, data: any) {
    return this.service.updateDepartmentUser(id, data).pipe(catchError(this.handleError));
  }

  delete(id: number) {
    return this.service.deleteDepartmentUser(id).pipe(catchError(this.handleError));
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
