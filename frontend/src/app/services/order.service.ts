import {Injectable} from "@angular/core";
import {FoodOrdersControllerService} from "./food-service-api";
import {catchError, Observable, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private service: FoodOrdersControllerService) {
  }

  findAll(loadOptions: any) {
    return this.service.getAllFoodOrdersLoad(loadOptions).pipe(catchError(this.handleError));
  }


  findOne(id: number): Observable<any> {
    return this.service.getFoodOrdersByOne(id).pipe(catchError(this.handleError));
  }

  save(data: any) {
    return this.service.createFoodOrders(data).pipe(catchError(this.handleError));
  }

  delete(id: number) {
    return this.service.deleteFoodOrders(id).pipe(catchError(this.handleError));
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
