import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { BehaviorSubject, Observable, switchMap, tap } from 'rxjs';
import { UserDetails } from '../../model/UserDetails';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private userDetailsSubject = new BehaviorSubject<UserDetails | null>(
    this.getUserFromLocalStorage()
  );
  userDetails$: Observable<UserDetails | null> =
    this.userDetailsSubject.asObservable();

  constructor(private http: HttpClient) {}

  loginUrl = `${environment.apiUrl}/userservice/auth/login`;
  getUserUrl = `${environment.apiUrl}/userservice/auth/user/`;

  // Step 1: Login and get token + role
  // Step 2: Use username to get user details and update the full user state
  login(userInfo: any): Observable<any> {
    return this.http.post<any>(this.loginUrl, userInfo).pipe(
      tap((res: any) => {
        localStorage.setItem('role', res.role);
        localStorage.setItem('token', res.token);
      }),
      switchMap(() => this.getUserDetails(userInfo.username)) // Chain the user details fetch
    );
  }

  // Get full user details and update state
  getUserDetails(username: string): Observable<any> {
    return this.http.get(`${this.getUserUrl}${username}`).pipe(
      tap((res: any) => {
        localStorage.setItem('id', res.id);
        localStorage.setItem('username', res.username);
        localStorage.setItem('email', res.email);

        const role = localStorage.getItem('role') || '';
        const token = localStorage.getItem('token') || '';

        const updatedUser: UserDetails = {
          id: res.id,
          username: res.username,
          email: res.email,
          role,
          token,
        };

        this.userDetailsSubject.next(updatedUser);
      })
    );
  }

  private getUserFromLocalStorage(): UserDetails | null {
    const id = localStorage.getItem('id');
    const username = localStorage.getItem('username');
    const email = localStorage.getItem('email');
    const role = localStorage.getItem('role');
    const token = localStorage.getItem('token');

    if (id && username && email) {
      return { id, username, email, role: role || '', token: token || '' };
    }
    return null;
  }

  logout() {
    localStorage.clear();
    this.userDetailsSubject.next(null);
  }
}
