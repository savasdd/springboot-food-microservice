import {Injectable} from "@angular/core";
import {GatewayService} from "../../services/gateway.service";
import {TokenService} from "./token.service";
import {TokenResponse, UserDto} from "../../services/api-gateway-api";
import {tap} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private service: GatewayService,
              private tokenService: TokenService) {
  }

  login(username: any, password: any) {
    const dto: UserDto = {
      username: username,
      password: password
    };

    return this.service.getToken(dto).pipe((tap((response: TokenResponse) => {
      if (response) {
        this.tokenService.saveToken(response.access_token);
        this.tokenService.saveRefreshToken(response.refresh_token);
        this.tokenService.saveUser(dto.username);
        this.tokenService.saveRol(response.roles);
      }
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
