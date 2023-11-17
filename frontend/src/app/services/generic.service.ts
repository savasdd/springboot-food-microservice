import {Injectable} from '@angular/core';
import {HttpClient, HttpContext, HttpEvent, HttpHeaders, HttpResponse} from '@angular/common/http';
import {catchError, firstValueFrom, Observable, throwError} from 'rxjs';
import {environment} from "../../environments/environment";
import {TokenService} from "../auth/service/token.service";
import {LoadResultDepartment} from "./user-service-api";


@Injectable({
  providedIn: 'root'
})
export class GenericService {
  private baseUrl: any = null;
  public defaultHeaders = new HttpHeaders();

  constructor(protected http: HttpClient, private service: TokenService) {
  }

  instance(rootPath: string): GenericService {
    const genericService: GenericService = new GenericService(this.http, this.service);
    genericService.baseUrl = environment.apiUrl + rootPath + '/';
    return genericService;
  }

  findOne(id: any) {
    return firstValueFrom(this.http.get(this.baseUrl + '' + id, {headers: this.defaultHeaders}));
  }

  public findAll(loadOptions: any, observe?: 'body', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json', context?: HttpContext}): Observable<any>;
  public findAll(loadOptions: any, observe?: 'response', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json', context?: HttpContext}): Observable<HttpResponse<any>>;
  public findAll(loadOptions: any, observe?: 'events', reportProgress?: boolean, options?: {httpHeaderAccept?: 'application/json', context?: HttpContext}): Observable<HttpEvent<any>>;
  public findAll(loadOptions: any, observe: any = 'body', reportProgress: boolean = false, options?: {httpHeaderAccept?: 'application/json', context?: HttpContext}): Observable<any> {
    if (loadOptions === null || loadOptions === undefined) {
      throw new Error('Required parameter dataSourceLoadOptionsDepartment was null or undefined when calling getAllDepartmentLoad.');
    }

    let localVarHeaders = this.defaultHeaders;

    let localVarHttpHeaderAcceptSelected: string | undefined = options && options.httpHeaderAccept;
    if (localVarHttpHeaderAcceptSelected !== undefined) {
      localVarHeaders = localVarHeaders.set('Accept', localVarHttpHeaderAcceptSelected);
    }

    let localVarHttpContext: HttpContext | undefined = options && options.context;
    if (localVarHttpContext === undefined) {
      localVarHttpContext = new HttpContext();
    }

    // to determine the Content-Type header
      localVarHeaders = localVarHeaders.set('Access-Control-Allow-Origin', '*');
      localVarHeaders = localVarHeaders.set('Content-Type', 'application/json');
      localVarHeaders = localVarHeaders.set('Access-Control-Allow-Methods', 'GET, POST, PATCH, PUT, DELETE, OPTIONS');
      localVarHeaders = localVarHeaders.set('Authorization', 'Bearer '+this.service.getToken());

    let responseType_: 'text' | 'json' | 'blob' = 'json';

    console.log(localVarHeaders)

    return this.http.request<LoadResultDepartment>('post', this.baseUrl+'all',
      {
        context: localVarHttpContext,
        body: loadOptions,
        responseType: <any>responseType_,
        // withCredentials: true,
        headers: localVarHeaders,
        observe: observe,
        reportProgress: reportProgress
      }
    );
  }

  // findAll(loadOptions: any) {
  //
  //   const localVarHeaders = new Headers({
  //     'Content-Type': 'application/json',
  //     'Authorization': `Bearer ${this.service.getToken()}`
  //   })
  //
  //   console.log(localVarHeaders)
  //   return firstValueFrom(this.http.post(this.baseUrl + 'all', loadOptions, {headers: localVarHeaders}));
  // }

  findAllGet(loadOptions: any) {
    return firstValueFrom(this.http.get(this.baseUrl + 'all?query=' + encodeURI(JSON.stringify(loadOptions))));
  }

  save(data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + '', data, {headers: this.defaultHeaders}));
  }

  update(key: any, data: any) {
    return firstValueFrom(this.http.put(this.baseUrl + '' + key, data, {headers: this.defaultHeaders}));
  }

  delete(key: any) {
    return firstValueFrom(this.http.delete(this.baseUrl + '' + key, {headers: this.defaultHeaders}));
  }

  customGet(path: string) {
    return firstValueFrom(this.http.get(this.baseUrl + path));
  }

  customPost(path: string, data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + path, data));
  }

  customPut(path: string, data: any) {
    return firstValueFrom(this.http.put(this.baseUrl + path, data));
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
