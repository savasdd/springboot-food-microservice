import {Injectable} from "@angular/core";
import {UserFileControllerService} from "./food-service-api";


@Injectable({
  providedIn: 'root'
})
export class UserFileService {

  constructor(private service: UserFileControllerService) {
  }

  loadImage(data: any, file: any) {
    return this.service.userFileUpload(data, file);
  }


}
