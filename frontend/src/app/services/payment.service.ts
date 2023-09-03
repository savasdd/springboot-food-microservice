import {Injectable} from "@angular/core";
import {CategoryControllerService} from "./food-service-api";
import {Observable} from "rxjs";
import {StockControllerService} from "./stock-service-api";
import {PaymentControllerService} from "./payment-service-api";


@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private service: PaymentControllerService) {
  }

  findAll(loadOptions: any) {
    return this.service.getAllPaymentLoad(loadOptions);
  }

  findOne(id: string): Observable<any> {
    return this.service.getByIdPayment(id);
  }

  save(data: any) {
    return this.service.createPayment(data);
  }

  update(id: string, data: any) {
    return this.service.updatePayment(id, data);
  }

  delete(id: string) {
    return this.service.deletePayment(id);
  }

}
