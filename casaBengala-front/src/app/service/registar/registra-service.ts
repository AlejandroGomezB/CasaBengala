import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistroInterface } from '../../interface/registro-interface';

@Injectable({
  providedIn: 'root',
})
export class RegistraService {
  private url: string = 'http://localhost:8080/gamefestival/demo/api/registro/v1/';

  constructor(private http: HttpClient){}

  public getRecords(): Observable<any> {
      return this.http.get<any>(this.url);
  }

  public postRecord(registro:RegistroInterface ) {
      return this.http.post(this.url,registro);
  }

}
