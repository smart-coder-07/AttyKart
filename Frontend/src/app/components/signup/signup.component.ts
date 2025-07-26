import { Component, inject, NgModule } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SignupService } from '../../services/Signup/signup.service';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

@Component({
  selector: 'app-signup',
  imports: [RouterLink, FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent {
  private notyf: Notyf;
  constructor(private service: SignupService, private router: Router) {
    this.notyf = new Notyf({
      position: {
        x: 'right',
        y: 'top',
      },
      duration: 3000,
      dismissible: true,
    });
  }
  userInfo: any = {
    username: '',
    email: '',
    password: '',
    role: '',
  };

  userRegister() {
    console.log(this.userInfo);
    if (
      this.userInfo.username.trim() != '' &&
      this.userInfo.password.trim() != '' &&
      this.userInfo.email.trim() != '' &&
      this.userInfo.role.trim() != ''
    ) {
      this.service.signup(this.userInfo).subscribe(
        (res: any) => {
          console.log(res);
          console.log(this.userInfo);

          this.notyf.success('User register successfully. Login please');
          this.userInfo = {};
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 3000);
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
