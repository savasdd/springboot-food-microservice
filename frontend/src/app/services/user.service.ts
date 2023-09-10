import {Injectable} from "@angular/core";
import {UserFileControllerService} from "./food-service-api";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private service: UserFileControllerService) {
  }

  loadImage(data: any) {
    return this.service.userFileUpload(data);
  }


}
