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
        url: 'foods/orders'
      },
      {
        name: 'Food',
        url: 'foods/foods'
      },
      {
        name: 'Stock',
        url: 'foods/stocks'
      },
      {
        name: 'Payment',
        url: 'foods/payments'
      },
      {
        name: 'Category',
        url: 'foods/categorys'
      },
    ]
  },
  {
    name: 'Department',
    url: '/data',
    iconComponent: {name: 'cilBookmark'},
    children: [
      {
        name: 'Department',
        url: 'data/department'
      },
    ]
  },
  {
    name: 'Auth',
    url: '/users',
    iconComponent: {name: 'cil-user'},
    children: [
      {
        name: 'User',
        url: 'users/user'
      },
      {
        name: 'Group',
        url: 'users/group'
      },
      {
        name: 'Rol',
        url: 'users/rol'
      },

    ]
  },
  {
    name: 'Logout',
    url: '/login',
    iconComponent: {name: 'cilLockLocked'},
    // children: [
    //   {
    //     name: 'Login',
    //     url: '/login'
    //   },
    //   // {
    //   //   name: 'Error 404',
    //   //   url: '/404'
    //   // },
    //   // {
    //   //   name: 'Error 500',
    //   //   url: '/500'
    //   // }
    // ]
  },
];
