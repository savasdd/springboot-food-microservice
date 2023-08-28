import {Injectable} from "@angular/core";
import {CategoryControllerService} from "./food-service-api";
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
