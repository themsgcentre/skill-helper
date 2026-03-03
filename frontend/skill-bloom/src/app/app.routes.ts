import { Routes } from '@angular/router';
import { ExploreComponent } from './explore/explore.component';
import { SharesComponent } from './shares/shares.component';
import { FavoritesComponent } from './favorites/favorites.component';
import { UserPageComponent } from './user/user-page/user-page.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'explore',
    pathMatch: 'full',
  },
  {
    path: 'explore',
    component: ExploreComponent
  },
  {
    path: 'shares',
    component: SharesComponent
  },
  {
    path: 'favorites',
    component: FavoritesComponent
  },
  {
    path: 'user',
    component: UserPageComponent
  }
];
