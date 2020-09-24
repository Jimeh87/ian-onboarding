import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PhoneService {

  private baseUrl = 'http://localhost:4200/api/v1/users';

  constructor(private http: HttpClient) { }

  getPhone(userId: number, phoneId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${userId}/phones/${phoneId}`);
  }

  createPhone(phone: Object, userId: number): Observable<Object> {
    return this.http.post(`${this.baseUrl}/${userId}/phones`, phone);
  }

  deletePhone(userId: number, phoneId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${userId}/phones/${phoneId}`, { responseType: 'text'});
  }

  getPhonesList(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${userId}/phones`);
  }
}
