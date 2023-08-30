import {Injectable} from "@angular/core";
import {Category, CategoryControllerService} from "./food-service-api";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private categoryService: CategoryControllerService) {
  }

  findAll(loadOptions: any) {
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

}
