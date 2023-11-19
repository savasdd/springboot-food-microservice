import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {catchError, firstValueFrom, Observable, throwError} from 'rxjs';
import {environment} from "../../environments/environment";
import {TokenService} from "../auth/service/token.service";
import {MessageService} from "./message.service";


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
    return firstValueFrom(this.http.get(this.baseUrl + '' + id, this.requestOptions).pipe(catchError(this.handleError)));
  }


  findAll(loadOptions: any) {
    return firstValueFrom(this.http.post<any>(this.baseUrl + 'all', loadOptions, this.requestOptions).pipe(catchError(this.handleError)));
  }


  save(data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + '', data, this.requestOptions).pipe(catchError(this.handleError)));
  }

  update(key: any, data: any) {
    return firstValueFrom(this.http.put(this.baseUrl + '' + key, data, this.requestOptions).pipe(catchError(this.handleError)));
  }

  delete(key: any) {
    return firstValueFrom(this.http.delete(this.baseUrl + '' + key, this.requestOptions).pipe(catchError(this.handleError)));
  }

  customGet(path: string) {
    return firstValueFrom(this.http.get(this.baseUrl + path, this.requestOptions).pipe(catchError(this.handleError)));
  }

  customPost(path: string, data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + path, data, this.requestOptions).pipe(catchError(this.handleError)));
  }

  customPostPermit(path: string, data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + path, data));
  }

  customPut(path: string, data: any) {
    return firstValueFrom(this.http.put(this.baseUrl + path, data, this.requestOptions));
  }

  findAllGet(loadOptions: any) {
    return firstValueFrom(this.http.get(this.baseUrl + 'all?query=' + encodeURI(JSON.stringify(loadOptions))).pipe(catchError(this.handleError)));
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

  private handleError(error: HttpErrorResponse) {
    const service = new MessageService;
    if (error.status === 0) {
      service.error(error.error.errorMessage);
      console.error('An error occurred:', error.error);
    } else {
      service.error(error.error.errorMessage);
      console.error(`Backend returned code ${error.status}, body was: `, error.error);
    }
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }


}
