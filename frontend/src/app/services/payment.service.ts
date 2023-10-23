import {Injectable} from "@angular/core";
import {catchError, Observable, throwError} from "rxjs";
import {PaymentControllerService} from "./payment-service-api";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";


@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private service: PaymentControllerService) {
  }

  findAll(loadOptions: any) {
    return this.service.getAllPaymentLoad(loadOptions).pipe(catchError(this.handleError));
  }

  findOne(id: string): Observable<any> {
    return this.service.getByIdPayment(id).pipe(catchError(this.handleError));
  }

  save(data: any) {
    return this.service.createPayment(data).pipe(catchError(this.handleError));
  }

  update(id: string, data: any) {
    return this.service.updatePayment(id, data).pipe(catchError(this.handleError));
  }

  delete(id: string) {
    return this.service.deletePayment(id).pipe(catchError(this.handleError));
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
