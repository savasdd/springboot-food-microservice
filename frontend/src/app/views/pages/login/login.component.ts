import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "../../../services/message.service";
import {TokenService} from "../../../auth/service/token.service";
import {AuthService} from "../../../auth/service/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  returnUrl!: string;

  loginForm = new FormGroup({
    username: new FormControl(null, [Validators.required]),
    password: new FormControl(null, [Validators.required]),
  });

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
    private token: TokenService,
    private authService: AuthService,) {
  }

  ngOnInit(): void {
    this.logout();
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/home';
  }

  login() {
    this.authService.login(this.loginForm.value.username, this.loginForm.value.password).then((data: any) => {
      this.router.navigate([this.returnUrl]);
    }, (err: any) => {
      console.log(err.error)
      this.messageService.error(err.error.errorMessage);
    });

  }


  logout() {
    this.token.removeUser();
    this.token.removeToken();
    this.router.navigate(['/login']);
  }

}
