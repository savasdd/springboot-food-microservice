import {Injectable} from "@angular/core";
import {catchError, Observable, throwError} from "rxjs";
import {Stock, StockControllerService} from "./stock-service-api";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";


@Injectable({
  providedIn: 'root'
})
export class StockService {

  constructor(private categoryService: StockControllerService) {
  }

  findAll(loadOptions: any) {
    return this.categoryService.getAllStockLoad(loadOptions).pipe(catchError(this.handleError));
  }

  findAlls(): Observable<Stock[]> {
    return this.categoryService.getAllStock().pipe(catchError(this.handleError));
  }

  findOne(id: string): Observable<any> {
    return this.categoryService.getStockOne(id).pipe(catchError(this.handleError));
  }

  save(data: any) {
    return this.categoryService.createStock(data).pipe(catchError(this.handleError));
  }

  update(id: string, data: any) {
    return this.categoryService.updateStock(id, data).pipe(catchError(this.handleError));
  }

  delete(id: string) {
    return this.categoryService.deleteStock(id).pipe(catchError(this.handleError));
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
