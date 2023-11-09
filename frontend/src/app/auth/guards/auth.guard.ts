import {Injectable, OnInit} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {TokenService} from "../service/token.service";


@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate, OnInit {

  constructor(private router: Router,
              private service: TokenService) {
  }

  ngOnInit(): void {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const user = this.service.getUser();
    const token = this.service.getToken();

    if (user && token) {
      return true;
    } else {
      this.router.navigate(['login'], {queryParams: {returnUrl: state.url}});
      return false;
    }
  }

}
