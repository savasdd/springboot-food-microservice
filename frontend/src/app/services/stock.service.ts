import {Injectable} from "@angular/core";
import {CategoryControllerService} from "./food-service-api";
import {Observable} from "rxjs";
import {StockControllerService} from "./stock-service-api";


@Injectable({
  providedIn: 'root'
})
export class StockService {

  constructor(private categoryService: StockControllerService) {
  }

  findAll(loadOptions: any) {
    return this.categoryService.getAllStockLoad(loadOptions);
  }

  findOne(id: string): Observable<any> {
    return this.categoryService.getStockOne(id);
  }

  save(data: any) {
    return this.categoryService.createStock(data);
  }

  update(id: string, data: any) {
    return this.categoryService.updateStock(id, data);
  }

  delete(id: string) {
    return this.categoryService.deleteStock(id);
  }

}
