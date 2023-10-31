import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {SessionStorageService} from "angular-web-storage";
import {TokenService} from "../service/token.service";
import {Observable} from "rxjs";


@Injectable({providedIn: 'root'})
export class RoleGuard {

  static forRoles(...allowedRoles: string[]) {

    @Injectable({
      providedIn: 'root'
    })
    class RoleCheck implements CanActivate {
      roles: Array<string> = [];

      constructor(private router: Router,
                  private sessionStorage: SessionStorageService,
                  private service: TokenService) {
      }


      canActivate(): Observable<boolean> | Promise<boolean> | boolean {

        let result = false;
        const currentUser = this.service.getRol();
        if (currentUser) {
          allowedRoles.forEach(role => {
            if (currentUser.roles.includes(role)) {
              result = true;
            }
          });
        }

        if (!result) {
          this.router.navigate(['/login']);
        }
        return result;
      }
    }

    return RoleCheck;
  }

}
