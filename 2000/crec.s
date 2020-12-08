	.file	"crec.c"
	.version	"01.01"
gcc2_compiled.:
.section	.rodata
.LC0:
	.string	"|"
.LC1:
	.string	"#"
.LC2:
	.string	" "
.LC3:
	.string	"|\n"
.text
	.align 16
.globl binaus
	.type	 binaus,@function
binaus:
	pushl %ebp
	movl %esp,%ebp
	subl $20,%esp
	pushl %ebx
	movl 8(%ebp),%ebx
	addl $-12,%esp
	pushl $.LC0
	call printf
	movl $0,b
	addl $16,%esp
	.p2align 4,,7
.L36:
	testb $1,%bl
	je .L37
	addl $-12,%esp
	pushl $.LC1
	jmp .L40
	.p2align 4,,7
.L37:
	addl $-12,%esp
	pushl $.LC2
.L40:
	call printf
	addl $16,%esp
	shrl $1,%ebx
	movl b,%eax
	leal 1(%eax),%edx
	movl %edx,b
	movl %edx,%eax
	cmpl $15,%eax
	jbe .L36
	addl $-12,%esp
	pushl $.LC3
	call printf
	movl -24(%ebp),%ebx
	movl %ebp,%esp
	popl %ebp
	ret
.Lfe1:
	.size	 binaus,.Lfe1-binaus
.section	.rodata
	.align 32
.LC4:
	.string	"\n\n ----------------   %3d Mrd  ...%ds / %ds total\n"
.LC5:
	.string	" ---------------- \n"
.text
	.align 16
.globl recurse
	.type	 recurse,@function
recurse:
	pushl %ebp
	movl %esp,%ebp
	subl $12,%esp
	pushl %edi
	pushl %esi
	pushl %ebx
	movl 8(%ebp),%edi
	decl %edi
	xorl %ebx,%ebx
	.p2align 4,,7
.L45:
	movl %ebx,para(,%edi,4)
	testl %edi,%edi
	je .L46
	addl $-12,%esp
	pushl %edi
	call recurse
	addl $16,%esp
	leal 1(%ebx),%esi
	jmp .L44
	.p2align 4,,7
.L46:
	movl pic,%eax
	leal 1(%eax),%edx
	movl %edx,pic
	movl %edx,%eax
	leal 1(%ebx),%esi
	cmpl $999999999,%eax
	jbe .L44
	addl $-12,%esp
	pushl $0
	call time
	movl %eax,%ecx
	movl %ecx,zende
	movl $0,pic
	movl c,%eax
	leal 1(%eax),%edx
	movl %edx,c
	movl %ecx,%edx
	subl ztotal,%edx
	pushl %edx
	subl zstart,%ecx
	pushl %ecx
	incl %eax
	pushl %eax
	pushl $.LC4
	call printf
	movl zende,%eax
	movl %eax,zstart
	movl $0,a
	addl $32,%esp
	movl $15,%ebx
	.p2align 4,,7
.L52:
	addl $-12,%esp
	movl %ebx,%eax
	subl a,%eax
	movl para(,%eax,4),%eax
	pushl %eax
	call binaus
	addl $16,%esp
	movl a,%eax
	leal 1(%eax),%edx
	movl %edx,a
	movl %edx,%eax
	cmpl $15,%eax
	jbe .L52
	addl $-12,%esp
	pushl $.LC5
	call printf
	addl $16,%esp
.L44:
	movl %esi,%ebx
	cmpl $65535,%ebx
	jbe .L45
	leal -24(%ebp),%esp
	popl %ebx
	popl %esi
	popl %edi
	movl %ebp,%esp
	popl %ebp
	ret
.Lfe2:
	.size	 recurse,.Lfe2-recurse
	.align 16
.globl main
	.type	 main,@function
main:
	pushl %ebp
	movl %esp,%ebp
	subl $8,%esp
	movl $0,a
	movl $para,%edx
	.p2align 4,,7
.L59:
	movl a,%eax
	movl $0,(%edx,%eax,4)
	leal 1(%eax),%ecx
	movl %ecx,a
	movl %ecx,%eax
	cmpl $15,%eax
	jbe .L59
	movl $16,d
	movl $0,pic
	movl $0,a
	movl $0,b
	movl $0,c
	addl $-12,%esp
	pushl $0
	call time
	movl %eax,zstart
	movl %eax,ztotal
	addl $-12,%esp
	movl d,%eax
	pushl %eax
	call recurse
	xorl %eax,%eax
	movl %ebp,%esp
	popl %ebp
	ret
.Lfe3:
	.size	 main,.Lfe3-main
	.comm	para,64,32
	.comm	a,4,4
	.comm	b,4,4
	.comm	c,4,4
	.comm	d,4,4
	.comm	pic,4,4
	.comm	ztotal,4,4
	.comm	zstart,4,4
	.comm	zende,4,4
	.ident	"GCC: (GNU) pgcc-2.95.2 19991024 (release)"
