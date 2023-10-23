import {Injectable} from "@angular/core";
import notify from 'devextreme/ui/notify';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor() {
  }

  success(message: string) {
    notify({message: message, width: 300, displayTime: 1500, type: 'success', shading: true}, {
      position: "top right",
      direction: "up-push"
    });
  }

  info(message: string) {
    notify({message: message, width: 300, displayTime: 1500, type: 'info', shading: true}, {
      position: "top right",
      direction: "up-push"
    });
  }

  error(message: string) {
    notify({message: message, width: 300, displayTime: 1500, type: 'error', shading: true}, {
      position: "top right",
      direction: "up-push"
    });
  }

  warning(message: string) {
    notify({message: message, width: 300, displayTime: 1500, type: 'warning', shading: true}, {
      position: "top right",
      direction: "up-push"
    });
  }



}
