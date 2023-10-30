import {Injectable} from "@angular/core";
import {Category, CategoryControllerService} from "./food-service-api";
import {catchError, firstValueFrom, Observable, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";
import {Department, DepartmentControllerService, LoadResultUserDepartment} from "./user-service-api";


@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(private service: DepartmentControllerService) {
  }

  findAll(loadOptions: any) {
    return firstValueFrom(this.service.getAllDepartmentLoad(loadOptions));
    //return this.service.getAllDepartmentUserLoad(loadOptions).pipe(catchError(this.handleError));
  }

  findAlls(): Observable<Department[]> {
    return this.service.getAllDepartment().pipe(catchError(this.handleError));
  }

  findOne(id: number) {
    return firstValueFrom(this.service.getOneDepartment(id).pipe(catchError(this.handleError)));
  }

  save(data: any) {
    return firstValueFrom(this.service.createDepartment(data).pipe(catchError(this.handleError)));
  }

  update(id: number, data: any) {
    return firstValueFrom(this.service.updateDepartment(id, data).pipe(catchError(this.handleError)));
  }

  delete(id: number) {
    return firstValueFrom(this.service.deleteDepartment(id).pipe(catchError(this.handleError)));
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
