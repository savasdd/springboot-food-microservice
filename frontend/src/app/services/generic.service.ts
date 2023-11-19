import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, firstValueFrom, Observable, throwError} from 'rxjs';
import {environment} from "../../environments/environment";
import {TokenService} from "../auth/service/token.service";


@Injectable({
  providedIn: 'root'
})
export class GenericService {
  private baseUrl: any = null;
  private readonly requestOptions: any;

  constructor(protected http: HttpClient, private service: TokenService) {
    this.requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.service.getToken()}`
      })
    };
  }

  instance(rootPath: string): GenericService {
    const genericService: GenericService = new GenericService(this.http, this.service);
    genericService.baseUrl = environment.apiUrl + rootPath + '/';
    return genericService;
  }

  findOne(id: any) {
    return firstValueFrom(this.http.get(this.baseUrl + '' + id, this.requestOptions));
  }


  findAll(loadOptions: any) {
    return firstValueFrom(this.http.post<any>(this.baseUrl + 'all', loadOptions, this.requestOptions));
  }

  findAllGet(loadOptions: any) {
    return firstValueFrom(this.http.get(this.baseUrl + 'all?query=' + encodeURI(JSON.stringify(loadOptions))));
  }

  save(data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + '', data, this.requestOptions));
  }

  update(key: any, data: any) {
    return firstValueFrom(this.http.put(this.baseUrl + '' + key, data, this.requestOptions));
  }

  delete(key: any) {
    return firstValueFrom(this.http.delete(this.baseUrl + '' + key, this.requestOptions));
  }

  customGet(path: string) {
    return firstValueFrom(this.http.get(this.baseUrl + path, this.requestOptions));
  }

  customPost(path: string, data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + path, data, this.requestOptions));
  }

  customPostPermit(path: string, data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + path, data));
  }

  customPut(path: string, data: any) {
    return firstValueFrom(this.http.put(this.baseUrl + path, data, this.requestOptions));
  }


  customPostHttpOptions(path: string, httpOptions: any, data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + path, data, httpOptions));
  }

  customGetHttpOptions(path: string, httpOptions: any) {
    return firstValueFrom(this.http.get(this.baseUrl + path, httpOptions));
  }

  customRawPost(path: string, data: any): Observable<any> {
    return this.http.post<any>(this.baseUrl + path, data);
  }


  saveObservable(data: any): Observable<any> {
    return this.postRequest(this.baseUrl + 'save', data);

  }


  protected postRequest(targetUrl: string, parameter: any): Observable<any> {
    var result = this.http.post(targetUrl, parameter);
    return result.pipe(catchError(
      (err, caught) => {
        return throwError(() => err);
      })
    );
  }


}
