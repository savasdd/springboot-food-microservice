import {Injectable} from "@angular/core";
import {catchError, Observable, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";
import {CategoryControllerService} from "./gateway-service-api/api/categoryController.service";
import {Category} from "./food-service-api";


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private categoryService: CategoryControllerService) {
  }

  findAll(loadOptions: any): any {
    return this.categoryService.getAllCategoryLoad(loadOptions);
  }

  findAlls(): Observable<Category[]> {
    return this.categoryService.getAllCategory();
  }

  findOne(id: number): Observable<any> {
    return this.categoryService.getCategoryByOne(id);
  }

  save(data: any) {
    return this.categoryService.createCategory(data);
  }

  update(id: number, data: any) {
    return this.categoryService.updateCategory(id, data);
  }

  delete(id: number) {
    return this.categoryService.deleteCategory(id);
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
