import {Injectable} from "@angular/core";
import {GatewayService} from "../gateway.service";
import {TokenService} from "./token.service";
import {TokenResponse, UserDto} from "../api-gateway-api";
import {tap} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private service: GatewayService,
              private tokenService: TokenService) {
  }

  login(username: string, password: string) {
    const dto: UserDto = {
      username: username,
      password: password
    };

    return this.service.getToken(dto).pipe((tap((response: TokenResponse) => {
      this.tokenService.saveToken(response.access_token);
      this.tokenService.saveRefreshToken(response.refresh_token);
      this.tokenService.saveUser(dto.username);
      this.tokenService.saveRol(response.roles);
    })));

  }

  refreshToken() {
    const token = this.tokenService.getToken();
    const dto: TokenResponse = {
      access_token: token,
    };

    return this.service.refreshToken(dto);
  }

}
