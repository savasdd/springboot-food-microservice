import {Component, OnInit} from '@angular/core';
import {GatewayService} from "../../../services/gateway.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private service: GatewayService) {
  }

  ngOnInit(): void {
  }

}
