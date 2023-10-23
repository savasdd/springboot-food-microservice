import {Injectable} from "@angular/core";
import {Category, CategoryControllerService} from "./food-service-api";
import {catchError, Observable, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private categoryService: CategoryControllerService) {
  }

  findAll(loadOptions: any) {
    return this.categoryService.getAllCategoryLoad(loadOptions).pipe(catchError(this.handleError));
  }

  findAlls(): Observable<Category[]> {
    return this.categoryService.getAllCategory().pipe(catchError(this.handleError));
  }

  findOne(id: number): Observable<any> {
    return this.categoryService.getCategoryByOne(id).pipe(catchError(this.handleError));
  }

  save(data: any) {
    return this.categoryService.createCategory(data).pipe(catchError(this.handleError));
  }

  update(id: number, data: any) {
    return this.categoryService.updateCategory(id, data).pipe(catchError(this.handleError));
  }

  delete(id: number) {
    return this.categoryService.deleteCategory(id).pipe(catchError(this.handleError));
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
