import {Injectable} from "@angular/core";
import {UserControllerService} from "./food-service-api";


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private service: UserControllerService) {
  }

  loadImage(data: any) {
    return this.service.userLoadImage(data);
  }


}
