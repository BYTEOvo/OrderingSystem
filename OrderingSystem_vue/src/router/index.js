import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/Login'
import Register from '../components/Register'
import MenuIndex from '../components/menu/MenuIndex'
import AdminIndex from '../components/admin/AdminIndex'
import Home from '../components/Home'
import CustomerIndex from '../components/customer/CustomerIndex'
import AdminDish from '../components/admin/AdminDish'
import EditDish from '../components/admin/EditDish'
import AddDish from '../components/admin/AddDish'
import AdminUser from '../components/admin/AdminUser'
import AdminOrder from '../components/admin/AdminOrder'
import CookIndex from '../components/cook/CookIndex'
import CookOrder from '../components/cook/Cookorder'
import CookMess from '../components/cook/CookMess'
import showRestaurant from '../components/customer/showRestaurant'
import CustomerCollect from '../components/customer/CustomerCollect'
import CustomerOrder from '../components/customer/CustomerOrder'
import CookInfo from '../components/cook/CookInfo'
import CustomerInfo from '../components/customer/CustomerInfo'
import AdminInfo from '../components/admin/AdminInfo'
import WaiterIndex from '../components/waiter/WaiterIndex'
import WaiterInfo from '../components/waiter/WaiterInfo'
import WaiterOrder from '../components/waiter/WaiterOrder'
import DishReady from '../components/waiter/DishReady'
import WaiterMess from '../components/waiter/WaiterMess'
import Table from '../components/waiter/Table'
import DashBoard from '../components/admin/DashBoard'
import ViewData from '../components/admin/ViewData'
import ViewBulletin from '../components/admin/ViewBulletin'
import AddBulletin2 from '../components/admin/AddBulletin2'
import AddBulletin1 from '../components/admin/AddBulletin1'
import AdminPay from '../components/admin/AdminPay'
import CustomerPay from '../components/customer/CustomerPay'
import CookMain from '../components/cook/CookMain'
import CustomerReviews from '../components/customer/CustomerReviews'
import CustomerGrade from '../components/customer/CustomerGrade'
import DeliveryIndex from '../components/delivery/DeliveryIndex'
import DeliveryInfo from '../components/delivery/DeliveryInfo'
import DeliveryMess from '../components/delivery/DeliveryMess'
import DeliveryMain from '../components/delivery/DeliveryMain'
import DeliveryOrder from '../components/delivery/DeliveryOrder'
import DeliverySend from '../components/delivery/DeliverySend'
import Pay from '../components/admin/Pay'

Vue.use(Router)
const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      redirect: '/login',
      component: Login
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/show',
      name: 'showRestaurant',
      component: showRestaurant,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/waiter/index',
      name: 'WaiterIndex',
      component: WaiterIndex,
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/waiter/info',
          name: 'WaiterInfo',
          component: WaiterInfo,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/waiter/order',
          name: 'WaiterOrder',
          component: WaiterOrder
        },
        {
          path: '/dish/ready',
          name: 'DishReady',
          component: DishReady
        },
        {
          path: '/waiter/mess',
          name: 'WaiterMess',
          component: WaiterMess
        },
        {
          path: '/table',
          name: 'Table',
          component: Table
        }
      ]
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/menu',
          name: 'MenuIndex',
          component: MenuIndex,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/admin/index',
          name: 'AdminIndex',
          component: AdminIndex,
          meta: {
            requireAuth: true
          },
          children: [
            {
              path: '/admin/dish',
              name: 'AdminDish',
              component: AdminDish,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/adddish',
              name: 'AddDish',
              component: AddDish,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/editdish',
              name: 'EditDish',
              component: EditDish,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/addbulletin1',
              name: 'AddBulletin1',
              component: AddBulletin1,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/addbulletin2',
              name: 'AddBulletin2',
              component: AddBulletin2,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/view/bulletin',
              name: 'ViewBulletin',
              component: ViewBulletin,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/pay',
              name: 'AdminPay',
              component: AdminPay,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/viewdata',
              name: 'ViewData',
              component: ViewData,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/dashboard',
              name: 'DashBoard',
              component: DashBoard,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/user',
              name: 'AdminUser',
              component: AdminUser,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/pay',
              name: 'Pay',
              component: Pay,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/order',
              name: 'AdminOrder',
              component: AdminOrder,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/admin/info',
              name: 'AdminInfo',
              component: AdminInfo,
              meta: {
                requireAuth: true
              }
            }
          ]
        },
        {
          path: '/delivery/index',
          name: 'DeliveryIndex',
          component: DeliveryIndex,
          meta: {
            requireAuth: true
          },
          children: [
            {
              path: '/delivery/info',
              name: 'DeliveryInfo',
              component: DeliveryInfo,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/delivery/mess',
              name: 'DeliveryMess',
              component: DeliveryMess,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/delivery/main',
              name: 'DeliveryMain',
              component: DeliveryMain,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/delivery/order',
              name: 'DeliveryOrder',
              component: DeliveryOrder,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/delivery/my/order',
              name: 'DeliverySend',
              component: DeliverySend,
              meta: {
                requireAuth: true
              }
            }
          ]
        },
        {
          path: '/customer/index',
          name: 'CustomerIndex',
          component: CustomerIndex,
          meta: {
            requireAuth: true
          },
          children: [
            { path: '/customer/collect',
              name: 'CustomerCollect',
              component: CustomerCollect,
              meta: {
                requireAuth: true
              }
            },
            { path: '/customer/order',
              name: 'CustomerOrder',
              component: CustomerOrder,
              meta: {
                requireAuth: true
              }
            },
            { path: '/customer/pay',
              name: 'CustomerPay',
              component: CustomerPay,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/customer/info',
              name: 'CustomerInfo',
              component: CustomerInfo,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/customer/grade',
              name: 'CustomerGrade',
              component: CustomerGrade,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/customer/reviews',
              name: 'CustomerReviews',
              component: CustomerReviews,
              meta: {
                requireAuth: true
              }
            }
          ]
        },
        {
          path: '/cook/index',
          name: 'cookIndex',
          component: CookIndex,
          meta: {
            requireAuth: true
          },
          children: [
            {
              path: '/cook/main',
              name: 'CookMain',
              component: CookMain,
              mata: {
                requireAuth: true
              }
            },
            {
              path: '/cook/order',
              name: 'cookOrder',
              component: CookOrder,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/cook/mess',
              name: 'cookMess',
              component: CookMess,
              meta: {
                requireAuth: true
              }
            },
            {
              path: '/cook/info',
              name: 'CookInfo',
              component: CookInfo,
              meta: {
                requireAuth: true
              }
            }
          ]
        }
      ]
    }
  ]
})
