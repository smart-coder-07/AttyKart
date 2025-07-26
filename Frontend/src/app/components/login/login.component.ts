import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { LoginService } from '../../services/Login/login.service';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [RouterLink, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  private notyf: Notyf;
  constructor(private service: LoginService, private router: Router) {
    this.notyf = new Notyf({
      position: {
        x: 'right',
        y: 'top',
      },
      duration: 2000,
      dismissible: true,
    });
  }

  userInfo = {
    username: '',
    password: '',
  };
  loginHandler() {
    if (
      this.userInfo.username.trim() !== '' &&
      this.userInfo.password.trim() !== ''
    ) {
      this.service.login(this.userInfo).subscribe(
        () => {
          this.notyf.success('Login successful');
          this.router.navigate(['/']);
        },
        (err) => {
          console.log(err);

          let errorMessage = 'Something went wrong.';

          if (err.error) {
            if (typeof err.error === 'string') {
              errorMessage = err.error;
            } else if (typeof err.error === 'object') {
              const messages = Object.values(err.error);
              if (messages.length) {
                errorMessage = messages.join(' | ');
              } else {
                errorMessage =
                  err.error.message ||
                  err.error.error ||
                  JSON.stringify(err.error);
              }
            }
          }

          this.notyf.error(errorMessage);
        }
      );
    } else {
      this.notyf.error('All fields are mandatory');
    }
  }
}
