import {INavData} from '@coreui/angular';

export const navItems: INavData[] = [
  {
    name: 'Dashboard',
    url: '/dashboard',
    iconComponent: {name: 'cil-speedometer'}
  },
  {
    name: 'Foods',
    url: '/foods',
    iconComponent: {name: 'cil-list'},
    children: [
      {
        name: 'Orders',
        url: '/foods/orders'
      },
      {
        name: 'Food',
        url: '/foods/foods'
      },
      {
        name: 'Stock',
        url: '/foods/stocks'
      },
      {
        name: 'Payment',
        url: '/foods/payments'
      },
      {
        name: 'Category',
        url: '/foods/categorys'
      },
    ]
  },
  {
    name: 'Login',
    url: '/login',
    iconComponent: {name: 'cil-star'},
    children: [
      {
        name: 'Login',
        url: '/login'
      },
      {
        name: 'Register',
        url: '/register'
      },
      {
        name: 'Error 404',
        url: '/404'
      },
      {
        name: 'Error 500',
        url: '/500'
      }
    ]
  },
];
