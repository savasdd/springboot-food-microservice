import {Injectable} from "@angular/core";
import {SessionStorageService} from "angular-web-storage";
import {Router} from "@angular/router";


const TOKEN_KEY = 'service-token';
const REFRESH_TOKEN_KEY = 'service-refresh-token';
const USER_KEY = 'service-user';
const ROL_KEY = 'service-rol';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(
    private sessionStorage: SessionStorageService,
    private router: Router
  ) {
  }


  public saveToken(token: any): void {
    this.sessionStorage.remove(TOKEN_KEY);
    this.sessionStorage.set(TOKEN_KEY, token);
  }

  public getToken(): any {
    return this.sessionStorage.get(TOKEN_KEY);
  }

  public saveRefreshToken(token: any): void {
    this.sessionStorage.remove(REFRESH_TOKEN_KEY);
    this.sessionStorage.set(REFRESH_TOKEN_KEY, token);
  }

  public getRefreshToken(): any {
    return this.sessionStorage.get(REFRESH_TOKEN_KEY);
  }

  public saveUser(username: any): void {
    this.sessionStorage.remove(USER_KEY);
    this.sessionStorage.set(USER_KEY, username);
  }

  public saveRol(rol: any): void {
    this.sessionStorage.remove(ROL_KEY);
    this.sessionStorage.set(ROL_KEY, rol);
  }

  public getUser(): string {
    return this.sessionStorage.get(USER_KEY);
  }


  public getRol(): any {
    return this.sessionStorage.get(ROL_KEY);
  }

  public removeToken(): void {
    this.sessionStorage.remove(TOKEN_KEY);
  }

  public removeUser(): void {
    this.sessionStorage.remove(USER_KEY);
  }

  public removeRol(): void {
    this.sessionStorage.remove(ROL_KEY);
  }

  signOut(): void {
    window.sessionStorage.clear();
    this.removeToken();
    this.removeUser();
    this.removeRol();
    this.router.navigate(['/login']);
  }

}
