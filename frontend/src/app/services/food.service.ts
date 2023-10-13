import {Injectable} from "@angular/core";
import {Food, FoodControllerService, FoodFileControllerService} from "./food-service-api";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private service: FoodControllerService, private fileService: FoodFileControllerService) {
  }

  findAll(loadOptions: any) {
    return this.service.getAllFoodLoad(loadOptions);
  }

  findAlls(): Observable<Food[]> {
    return this.service.getAllFood();
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

  getAllImage(id: any) {
    return this.fileService.getAllFoodFile(id);
  }

  deleteImage(name: any) {
    return this.fileService.deleteFoodFile(name);
  }

  uploadImage(id: any, fileName: any, fileType: any, file: any) {
    return this.fileService.foodFileUpload(id, fileName, fileType, file);
  }

}
