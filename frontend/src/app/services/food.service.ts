import {Injectable} from "@angular/core";
import {FoodControllerService} from "./food-service-api";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private service: FoodControllerService) {
  }

  findAll(loadOptions: any) {
    return this.service.getAllFoodLoad(loadOptions);
  }

  findOne(id: string): Observable<any> {
    return this.service.getFoodByOne(id);
  }

  save(data: any) {
    return this.service.createFood(data);
  }

  update(id: string, data: any) {
    return this.service.updateFood(id, data);
  }

  delete(id: string) {
    return this.service.deleteFood(id);
  }

}
