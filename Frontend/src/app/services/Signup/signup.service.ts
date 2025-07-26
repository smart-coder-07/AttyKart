import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SignupService {
  constructor(private http: HttpClient) {}
  apiUrl = `${environment.apiUrl}/userservice/auth/register`;

  signup(userInfo: any) {
    return this.http.post(this.apiUrl, userInfo);
  }
}
