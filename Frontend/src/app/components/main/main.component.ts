import { Component } from '@angular/core';
import { ProductsComponent } from '../products/products.component';
import { TrackComponent } from '../track/track.component';
import { TestimonialsComponent } from '../testimonials/testimonials.component';

@Component({
  selector: 'app-main',
  imports: [ProductsComponent, TrackComponent, TestimonialsComponent],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css',
})
export class MainComponent {}
