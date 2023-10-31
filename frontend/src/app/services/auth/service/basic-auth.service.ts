import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {SessionStorageService} from "angular-web-storage";
import {TokenService} from "./token.service";


@Injectable({
  providedIn: 'root'
})
export class BasicAuthService implements CanActivate {
  constructor(
    private router: Router,
    private sessionStorage: SessionStorageService,
    private token: TokenService,
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.token.getUser();
    if (currentUser) {
      return true;
    } else {
      this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
      return false;
    }
  }
}
