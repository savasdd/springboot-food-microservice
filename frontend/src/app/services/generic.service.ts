import {Injectable} from '@angular/core';
import {HttpClient, HttpContext, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {catchError, firstValueFrom, Observable, throwError} from 'rxjs';
import {environment} from "../../environments/environment";
import {TokenService} from "../auth/service/token.service";
import {MessageService} from "./message.service";
import {FoodFileDto} from "./food-service-api";


@Injectable({
  providedIn: 'root'
})
export class GenericService {
  private baseUrl: any = null;

  constructor(protected http: HttpClient, private service: TokenService) {
  }

  instance(rootPath: string): GenericService {
    const genericService: GenericService = new GenericService(this.http, this.service);
    genericService.baseUrl = environment.apiUrl + rootPath + '/';
    return genericService;
  }

  findOne(id: any) {
    return firstValueFrom(this.http.get(this.baseUrl + 'getOne/' + id).pipe(catchError(this.handleError)));
  }


  findAll(loadOptions: any) {
    return firstValueFrom(this.http.post<any>(this.baseUrl + 'all', loadOptions).pipe(catchError(this.handleError)));
  }


  save(data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + 'save', data).pipe(catchError(this.handleError)));
  }

  update(key: any, data: any) {
    return firstValueFrom(this.http.put(this.baseUrl + 'update/' + key, data).pipe(catchError(this.handleError)));
  }

  delete(key: any) {
    return firstValueFrom(this.http.delete(this.baseUrl + 'delete/' + key).pipe(catchError(this.handleError)));
  }

  customGet(path: string) {
    return firstValueFrom(this.http.get(this.baseUrl + path).pipe(catchError(this.handleError)));
  }

  customPost(path: string, data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + path, data).pipe(catchError(this.handleError)));
  }

  customPostPermit(path: string, data: any) {
    return firstValueFrom(this.http.post(this.baseUrl + path, data));
  }

  customPut(path: string, data: any) {
    return firstValueFrom(this.http.put(this.baseUrl + path, data));
  }

  customDelete(path: string) {
    return firstValueFrom(this.http.delete(this.baseUrl + path));
  }

  findAllGet(loadOptions: any) {
    return firstValueFrom(this.http.get(this.baseUrl + 'all?query=' + encodeURI(JSON.stringify(loadOptions))).pipe(catchError(this.handleError)));
  }


  protected postRequest(targetUrl: string, parameter: any): Observable<any> {
    var result = this.http.post(targetUrl, parameter);
    return result.pipe(catchError(
      (err, caught) => {
        return throwError(() => err);
      })
    );
  }

  public fileUpload(path: string, foodId: string, fileName: any, fileType: any, file: any, observe: any = 'body', reportProgress: boolean = false, options?: {
    httpHeaderAccept?: 'application/json',
    context?: HttpContext
  }): Observable<any> {
    let localVarHttpContext: HttpContext | undefined = options && options.context;
    if (localVarHttpContext === undefined) {
      localVarHttpContext = new HttpContext();
    }

    // to determine the Content-Type header
    const consumes: string[] = [
      'multipart/form-data',
      'application/json'
    ];

    const canConsumeForm = this.canConsumeForm(consumes);

    let localVarFormParams: { append(param: string, value: any): any; };
    let localVarUseForm = false;
    let localVarConvertFormParamsToString = false;
    localVarUseForm = canConsumeForm;
    if (localVarUseForm) {
      localVarFormParams = new FormData();
    } else {
      localVarFormParams = new HttpParams();
    }

    if (foodId !== undefined) {
      localVarFormParams = localVarFormParams.append('foodId', <any>foodId) as any || localVarFormParams;
    }
    if (fileName !== undefined) {
      localVarFormParams = localVarFormParams.append('fileName', <any>fileName) as any || localVarFormParams;
    }
    if (fileType !== undefined) {
      localVarFormParams = localVarFormParams.append('fileType', <any>fileType) as any || localVarFormParams;
    }
    if (file !== undefined) {
      localVarFormParams = localVarFormParams.append('file', <any>file) as any || localVarFormParams;
    }

    let responseType_: 'text' | 'json' | 'blob' = 'json';
    return this.http.request<FoodFileDto>('post', this.baseUrl + path,
      {
        context: localVarHttpContext,
        body: localVarConvertFormParamsToString ? localVarFormParams.toString() : localVarFormParams,
        responseType: <any>responseType_,
        observe: observe,
        reportProgress: reportProgress
      }
    );
  }


  private canConsumeForm(consumes: string[]): boolean {
    const form = 'multipart/form-data';
    for (const consume of consumes) {
      if (form === consume) {
        return true;
      }
    }
    return false;
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
