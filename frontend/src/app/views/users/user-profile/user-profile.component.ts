import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  @ViewChild('form') form: any = NgForm;


  constructor() {
  }

  ngOnInit(): void {
  }


  updateClick() {
    console.log("images")
  }


}
