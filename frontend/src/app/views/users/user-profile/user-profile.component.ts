import {Component, OnInit, ViewChild} from '@angular/core';
import {DxFormComponent} from "devextreme-angular";
import {UserService} from "../../../services/user.service";
import notify from "devextreme/ui/notify";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  userData: UserModel = new UserModel();
  @ViewChild(DxFormComponent, {static: false}) form: any = DxFormComponent;


  constructor(private service: UserService) {
  }

  ngOnInit(): void {
  }

  onValueChanged(e: any) {
    const file = e.value[0];
    const fileReader = new FileReader();
    fileReader.onload = () => {
      this.userData.fileData = fileReader.result as ArrayBuffer;
    }
    fileReader.readAsDataURL(file);
  }

  updateClick() {
    const formValid = this.form.instance.validate();
    if (formValid && this.userData.fileData != null) {
      this.service.loadImage(this.userData).subscribe((response: any) => {
        notify({message: "Upload Success"});
      });
    }
  }


}

export class UserModel {
  userId?: string = "a877121255f8f32a";
  firstName?: string = "Savaş";
  lastName?: string = "Dede";
  fileData: any;

  constructor() {
  }
}
