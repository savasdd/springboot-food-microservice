import {Injectable} from "@angular/core";
import {CategoryControllerService, FoodControllerService} from "./food-service-api";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private service: CategoryControllerService) {
  }

  findAll(loadOptions: any) {
    return this.service.getAllCategoryFood(loadOptions);
  }

  findOne(id: number): Observable<any> {
    return this.service.getCategoryByOne(id);
  }

  save(data: any) {
    return this.service.createCategory(data);
  }

  update(id: number, data: any) {
    return this.service.updateCategory(id, data);
  }

  delete(id: number) {
    return this.service.deleteCategory(id);
  }

}
