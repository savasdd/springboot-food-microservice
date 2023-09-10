import {Component, OnInit, ViewChild} from '@angular/core';
import {DxFormComponent} from "devextreme-angular";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  // @ViewChild('form') form: any = NgForm;
  userData: UserModel = new UserModel();
  @ViewChild(DxFormComponent, {static: false}) form: any = DxFormComponent;

  constructor() {
  }

  ngOnInit(): void {
  }


  updateClick() {
    const formValid = this.form.instance.validate();
    if (formValid) {
      console.log(this.userData)
    }
  }


}

export class UserModel {
  userId?: string;
  firstName?: string;
  lastName?: string;
  image: any;

  constructor() {
  }
}
