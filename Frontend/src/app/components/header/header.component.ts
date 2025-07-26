import { NgClass, NgIf } from '@angular/common';
import { Component, HostListener } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

@Component({
  selector: 'app-header',
  imports: [NgClass, NgIf, RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  private notyf: Notyf;
  constructor(private router: Router) {
    this.notyf = new Notyf({
      position: {
        x: 'right',
        y: 'top',
      },
      duration: 3000,
      dismissible: true,
    });
  }
  isOpen: boolean = false;
  dropdownVisible: boolean = false;
  userDetails = {
    id: localStorage.getItem('id'),
    username: localStorage.getItem('username'),
    email: localStorage.getItem('email'),
    role: localStorage.getItem('role'),
    token: localStorage.getItem('token'),
  };

  toggleDropdown(): void {
    this.dropdownVisible = !this.dropdownVisible;
  }

  @HostListener('document:click', ['$event'])
  onClick(event: MouseEvent): void {
    const clickedInside =
      event.target instanceof HTMLElement &&
      (event.target.closest('button') || event.target.closest('.relative'));

    if (!clickedInside) {
      this.dropdownVisible = false;
    }
  }
  logout() {
    localStorage.clear();
    this.notyf.success('Logout successfully');
    window.location.reload();
  }
}
