import request from '../utils/request'



export const register=(data)=>request.post('/user/register',data)
// ========== 用户相关 ==========
export const login = (data) => request.post('/user/login', data)
export const logout = () => request.post('/user/logout')
export const getUserInfo = () => request.get('/user/info')
export const getUserList = () => request.get('/user/list')
export const addUser = (data) => request.post('/user/add', data)
export const updatePwd = (params) => request.post('/user/updatePwd', null, { params })
export const deleteUser = (uid) => request.delete(`/user/delete/${uid}`)

// ========== 图书类型 ==========
export const getBookTypeList = () => request.get('/booktype/list')
export const addBookType = (data) => request.post('/booktype/add', data)
export const updateBookType = (data) => request.post('/booktype/update', data)
export const deleteBookType = (id) => request.delete(`/booktype/delete/${id}`)

// ========== 读者类型 ==========
export const getReaderTypeList = () => request.get('/readertype/list')
export const addReaderType = (data) => request.post('/readertype/add', data)
export const updateReaderType = (data) => request.post('/readertype/update', data)
export const deleteReaderType = (id) => request.delete(`/readertype/delete/${id}`)

// ========== 图书 ==========
export const getBookList = () => request.get('/book/list')
export const searchBooks = (params) => request.get('/book/search', { params })
export const addBook = (data) => request.post('/book/add', data)
export const updateBook = (data) => request.post('/book/update', data)
export const deleteBook = (bisbn) => request.delete(`/book/delete/${bisbn}`)

// ========== 读者 ==========
export const getReaderList = () => request.get('/reader/list')
export const searchReaders = (params) => request.get('/reader/search', { params })
export const addReader = (data) => request.post('/reader/add', data)
export const updateReader = (data) => request.post('/reader/update', data)
export const deleteReader = (rid) => request.delete(`/reader/delete/${rid}`)

// ========== 借阅 ==========
export const getBorrowList = () => request.get('/borrow/list')
export const getAllBorrow = () => request.get('/borrow/all')
export const getMyBorrow = () => request.get('/borrow/my')
export const borrowBook = (params) => request.post('/borrow/borrow', null, { params })
export const returnBook = (params) => request.post('/borrow/return', null, { params })
//操作记录

export const getLogs = () => request.get('/log/list')