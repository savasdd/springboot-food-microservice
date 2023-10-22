import {Injectable} from "@angular/core";
import {FoodOrdersControllerService} from "./food-service-api";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private service: FoodOrdersControllerService) {
  }

  findAll(loadOptions: any) {
    return this.service.getAllFoodOrdersLoad(loadOptions);
  }


  findOne(id: string): Observable<any> {
    return this.service.getFoodOrdersByOne(id);
  }

  save(data: any) {
    return this.service.createFoodOrders(data);
  }


  delete(id: string) {
    return this.service.deleteFoodOrders(id);
  }

}
