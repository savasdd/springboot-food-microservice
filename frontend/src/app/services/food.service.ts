import {Injectable} from "@angular/core";
import {catchError, Observable, throwError} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {MessageService} from "./message.service";
import {Food, FoodControllerService, FoodFileControllerService} from "./food-service-api";


@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private service: FoodControllerService, private fileService: FoodFileControllerService) {
  }

  findAll(loadOptions: any): any {
    return this.service.getAllFoodLoad(loadOptions).pipe(catchError(this.handleError));
  }

  findAllOrder(loadOptions: any): any {
    return this.service.getAllFoodLoadOrder(loadOptions).pipe(catchError(this.handleError));
  }

  findAlls(): Observable<Food[]> {
    return this.service.getAllFood().pipe(catchError(this.handleError));
  }


  findOne(id: number): Observable<any> {
    return this.service.getFoodByOne(id).pipe(catchError(this.handleError));
  }

  save(data: any) {
    return this.service.createFood(data).pipe(catchError(this.handleError));
  }

  update(id: number, data: any) {
    return this.service.updateFood(id, data).pipe(catchError(this.handleError));
  }

  delete(id: number) {
    return this.service.deleteFood(id).pipe(catchError(this.handleError));
  }

  getAllImage(id: any) {
    return this.fileService.getAllFoodFile(id).pipe(catchError(this.handleError));
  }

  deleteImage(name: any) {
    return this.fileService.deleteFoodFile(name).pipe(catchError(this.handleError));
  }

  uploadImage(id: any, fileName: any, fileType: any, file: any) {
    return this.fileService.foodFileUpload(id, fileName, fileType, file);
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
